if [ $JAVA_HOME ]
then
	JAVA_EXE=$JAVA_HOME/bin/java
	CLASSPATH="../dist/lib/minisip-logging-server-0.1.jar"
	$JAVA_EXE -cp ".:${CLASSPATH}" csd.minisip.logging.LoggingServer $1
else
	echo "JAVA_HOME Environment variable not set"
fi
