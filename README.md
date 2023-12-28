# PieceTable Library

## Overview

`piece-table-library` is a Java library providing an implementation of a Piece Table, a data structure commonly used in text editors for efficient text manipulation. It offers a robust solution for applications that require dynamic text editing capabilities.

## Prerequisites

- Java JDK 17 or higher.
- Maven (for building the library and running tests).

## Building the Library

To build the library, navigate to the project root directory and run:

```bash
mvn clean install
```

This command will compile the library and run the unit tests.

## Using the Library

To use this library in your project, build it locally, then include the following dependency in your Maven `pom.xml` file:

```xml
<dependency>
    <groupId>com.martinsundeqvist</groupId>
    <artifactId>piece-table-library</artifactId>
    <version>1.0</version>
</dependency>
```

## Running Tests

To execute the test cases separately, use:

```bash
mvn test
```

## Contributing

Contributions to enhance `piece-table-library` are welcome! Feel free to fork the repository, make your changes, and submit a pull request. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.TXT) file for details.


## To-Do
- [ ] Sign with PGP key and add to maven repository
- [ ] Implement multi-character delete
- [ ] Add "insert" method to wrap "addPiece"
- [ ] Update pieces to use linked list rather than dynamic array
