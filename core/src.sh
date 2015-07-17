mvn source:aggregate
rm -rf ./target/src
mkdir ./target/src
cp ./target/tmis-core-2.6.0-SNAPSHOT-sources.jar  ./target/src/tmis-core-2.6.0-SNAPSHOT-sources.jar 
cd ./target/src
"${JAVA_HOME}"/bin/jar xf ./tmis-core-2.6.0-SNAPSHOT-sources.jar 
cd ../../
rm ./target/src/tmis-core-2.6.0-SNAPSHOT-sources.jar 
rm ./target/tmis-core-2.6.0-SNAPSHOT-sources.jar 
cp -r ./*/src/main/scala/* ./target/src
mvn dependency:list | sort | uniq  | grep "\[INFO\]    " > ./target/dependency.txt 