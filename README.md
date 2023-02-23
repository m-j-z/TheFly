
# INSTRUCTIONS

## HOW TO BUILD

1. Open a command prompt and change into the parent directory of the `src` directory.
2. Run the following command on the command line.
> mvn clean package

## HOW TO RUN

1. Open a command prompt and change into the `target` directory.
2. Run the following command on the command line.
> java -cp TheFly-1.0-SNAPSHOT.jar Main

## HOW TO TEST

1. Open a command prompt and change into the parent directory of the `src` directory.
2. Run the following command on the command line to conduct all tests.
> mvn test

## HOW TO CREATE ARTIFACTS
1. Open a command prompt and change into the parent directory of the `src` directory.
2. Run the following command on the command line.
> mvn clean deploy

## HOW TO CREATE JAVADOCS
1. Open a command prompt and change into the parent directory of the `src` directory.
2. Run the following command on the command line to create JAVADOCS.
> mvn clean javadoc:javadoc

# ARTIFACTS LOCATED AT
> https://csil-git1.cs.surrey.sfu.ca/cmpt276S22_group8/project/-/packages/3

### Installation

Copy and paste this inside your pom.xml dependencies block.
```
<dependency>
  <groupId>org.groupeight.fly</groupId>
  <artifactId>TheFly</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

Run Maven command.
> mvn dependency:get -Dartifact=org.groupeight.fly:TheFly:1.0-SNAPSHOT

Setup registry as needed.
```
<repositories>
  <repository>
    <id>gitlab-maven</id>
    <url>https://csil-git1.cs.surrey.sfu.ca/api/v4/projects/31305/packages/maven</url>
  </repository>
</repositories>

<distributionManagement>
  <repository>
    <id>gitlab-maven</id>
    <url>https://csil-git1.cs.surrey.sfu.ca/api/v4/projects/31305/packages/maven</url>
  </repository>

  <snapshotRepository>
    <id>gitlab-maven</id>
    <url>https://csil-git1.cs.surrey.sfu.ca/api/v4/projects/31305/packages/maven</url>
  </snapshotRepository>
</distributionManagement>
```