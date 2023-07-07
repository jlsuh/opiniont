# Opinion't

App para quitarse de encima las encuestas docentes _`"No Opino"`-ando_.

## Specs

```bash
$ javac -version
javac 19.0.2
```

## Run

```bash
$ mvn clean install && mvn exec:java -Dexec.mainClass=com.jlsuh.opiniont.App -Dexec.cleanupDaemonThreads=false -Dexec.args="arg1 arg2"
# Siendo "arg1" el "Usuario" (username) y "arg2" la "Contraseña" del SIU Guaraní.
```

O bien mediante IntelliJ, configurando ambos argumentos en "Select Run/Debug Configuration" > "Edit Configurations..."
