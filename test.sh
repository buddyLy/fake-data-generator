#!/bin/bash

foo()
{
	echo "lcle"
}

bar()
{
	username=$(foo)
	echo $username
}

bar
