Before Building the project you will need to manually add the Wicket-Bean Validation project's jar to your maven repo.

Please follow the steps at http://blog.zenika.com/index.php?post/2010/02/24/Wicket-JSR-303-Validators

In Short:
1) Download http://code.google.com/p/wicket-jsr303-validators/downloads/detail?name=wicket-jsr303-validators-1.0-SNAPSHOT.jar

2) Open a terminal and add the jar file mentioned above to your local maven repository (you will need to overrice the -Dfile argument):

$ mvn install:install-file -Dfile=/path/to/wicket-jsr303-validators-1.0-SNAPSHOT.jar \
                                 -DgroupId=com.zenika.wicket.contrib \
                                 -DartifactId=wicket-jsr303-validators \
                                 -Dversion=1.0-SNAPSHOT -Dpackaging=jar