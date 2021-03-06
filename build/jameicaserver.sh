#!/bin/sh

# Linux Start-Script fuer Server-Betrieb.
# Jameica wird hierbei OHNE GUI gestartet.

link=$(readlink -f "$0")
dir=$(dirname "$link")
cd "$dir" 

#_JCONSOLE="-Dcom.sun.management.jmxremote"

# https://www.willuhn.de/bugzilla/show_bug.cgi?id=798

export JDK_JAVA_OPTIONS='--add-modules=java.se.ee'
java -Djava.net.preferIPv4Stack=true -Xmx512m -Xss64m $_JCONSOLE -jar jameica-linux.jar -d $@
