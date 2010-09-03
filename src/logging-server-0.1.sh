if [ $JAVA_HOME ]
	
then
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
	

	$JAVA_HOME -cp ".:${CLASSPATH}" csd.minisip.logging.LoggingServer $PORT $LOG_DIRECTORY
else
	echo "Could not locate the java executable."
	echo "Please set the JAVA_HOME environment variable to point at it."
	echo ""
	echo "For example using this command:"
	echo "echo \"export JAVA_HOME=/usr/bin/java\" >> ~/.bashrc;. ~/.bashrc"
fi
