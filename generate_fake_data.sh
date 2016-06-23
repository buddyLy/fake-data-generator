#!/bin/bash

init()                                                                                                                                                                                                    
{
	set -e #stop on errors
	#set -x #print commands that are run.
	set -u #error on unbound variable
	set -o pipefail #captures errors on piped commands

	num_records=100
	filesize_in_megs=2
	megs_divisor=$(echo "$filesize_in_megs * 1024" | bc)
	data_file="rtdata"
	tmp_data_file="rtdatatmp.txt"
	file_index=1
	/bin/rm ./data/${data_file}* || true
	/bin/rm ${tmp_data_file} || true
	touch ${tmp_data_file} 
}

flip_file()
{
	size=$(du -k ${data_file} | awk '{print $1}')
	current_size=$(echo "$size/$megs_divisor" | bc)
	if ((current_size > filesize_in_megs));then
		cp ${data_file} ${data_file}${file_index}.txt   
		echo "" > ${data_file}
		echo "flipped file: ${data_file}${file_index}.txt"
		((file_index++))
	fi  
}

function get_time_elapsed
{
	start_time=$1
	end_time=$2 #$SECONDS
	time_elapsed_in_sec=$(echo "($end_time-$start_time)"|bc -l) 
	time_elapsed_in_min=$(echo "($end_time-$start_time)/60"|bc -l|xargs printf "%.2f")
	time_elapsed_in_min_round_up=$(echo "($end_time-$start_time)/60"|bc -l|xargs printf "%1.f") 
}

function split_by_size
{
	file_tobe_split=$1;shift
	splitsize_in_megs=$1	

	totalsize_in_megs=$(du -m ${file_tobe_split} | awk '{print $1}')
	total_lines=$(wc -l ${file_tobe_split} | awk '{print $1}')
	
	split_lines=$(echo "(${total_lines}*${splitsize_in_megs}) / ${totalsize_in_megs}" | bc)
	split -l ${split_lines} ${tmp_data_file} ./data/${data_file}
}

display_performance()
{
	split_by_size "${tmp_data_file}" "${filesize_in_megs}"
	num_of_files=$(ls ./data/${data_file}* | wc -l)
	printf "Number of records generated: %s\nNumber of files created: %s\nRun time in min|sec: %s|%s\n" "${num_records}" "${num_of_files}" "${time_elapsed_in_min_round_up}" "${time_elapsed_in_sec}"
}

move_to_dev6()
{
	#move to jars and script to dev6 
	scp ./target/fake-data-geneartor-1.0-SNAPSHOT-jar-with-dependencies.jar tstr400187.wal-mart.com:/u/users/lcle/fake-data-generator/target/
	scp generate_fake_data.sh tstr400187.wal-mart.com:/u/users/lcle/fake-data-generator/
}	

move_to_hdfs()
{
	hadoop fs -put -f /u/users/lcle/fake-data-generator/data/* /user/workspace/ckp_realtime_matching/delta_cpm_input 
}

main()
{
	init
	start=${SECONDS}
	#get_fake_data
	java -jar ./target/fake-data-geneartor-1.0-SNAPSHOT-jar-with-dependencies.jar "${num_records}" "${tmp_data_file}"
	end=${SECONDS}
	get_time_elapsed ${start} ${end}    
	display_performance
	#move_to_hdfs
}

main
#move_to_dev6

#---unit test---#
(
[[ "${BASH_SOURCE[0]}" == "$0" ]] || exit 1
echo "kicking off unit tests"
split_by_size_test()
{
	split_by_size "${tmp_data_file}" "${filesize_in_megs}"
}

#init
#split_by_size_test
)
