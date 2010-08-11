if [ $JAVA_HOME ]
then
	JAVA_EXE=$JAVA_HOME/bin/java
	$JAVA_EXE -jar minisip-logging-server-1.0.jar $1
else
	echo "JAVA_HOME Environment variable not set"
fi
