if [ -f /usr/bin/java ]
then
    export JAVA_HOME=/usr
fi

if [ $JAVA_HOME ]
then
        # Changing the pwd to the location of this very file
	abspath=$(cd ${0%/*} && echo $PWD/${0##*/})
	path_only=`dirname "$abspath"`
	cd $path_only

	CLASSPATH="/etc/minisip_logging/lib/minisip-logging-server-0.1.jar"
	
	PORT=8700
	if [ "$1" = "-p" ]
	then
		PORT=$2
	fi

	LOG_DIRECTORY=$HOME/minisiplogs
	if [ "$2" = "-ld" ]
	then 
		LOG_DIRECTORY=$2
	fi

	CRASH_DIRECTORY="../minisipcrash_report"
	if [ "$3" = "-cd" ]
	then
		CRASH_DIRECTORY=$3
	fi	

	$JAVA_HOME/bin/java -cp ".:${CLASSPATH}" csd.minisip.logging.LoggingServer $PORT $LOG_DIRECTORY $CRASH_DIRECTORY
else
	echo ""
	echo "Could not locate the java executable."
	echo "Please set the JAVA_HOME environment variable to point at it."
	echo ""
	echo "On Linux you can use this command to do that:"
	echo "echo \"export JAVA_HOME=/usr\" >> ~/.bashrc;. ~/.bashrc"
	echo ""
	sleep 2
fi
