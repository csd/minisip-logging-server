if [ $JAVA_HOME ]
then
	JAVA_EXE=$JAVA_HOME/bin/java
	CLASSPATH="../dist/lib/minisip-logging-server-0.1.jar"
	
	PORT=2222
	if [ "$1" = "-p" ]
	then
		PORT=$2
	fi

	if [ "$3" = "-p" ]
	then
		PORT=$4
	fi

	LOG_DIRECTORY="../minisiplogs"
	if [ "$1" = "-d" ]
	then 
		LOG_DIRECTORY=$2
	fi

	if [ "$3" = "-d" ]
	then
		LOG_DIRECTORY=$4
	fi
	

	$JAVA_EXE -cp ".:${CLASSPATH}" csd.minisip.logging.LoggingServer $PORT $LOG_DIRECTORY
else
	echo "JAVA_HOME Environment variable not set"
fi
