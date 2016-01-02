FastStorage
============

Simple data storage based on CSV

**Current version:** *2.0*

ECSV is an enhanced format based on CSV. Main killer-feature is basic array and key-value pairs supporting. This library providing default implementation of ECSV format parser and packer. Parser is for reading entities form string, packer otherwise pack entity to string.

ECSV parser doesn't use any reflection or annotations, so it should be extremely fast. See examples of ECSV formatted output in test files. Also check out ExampleDirectory as example of ECSV entity implementation.

## New features in 2.0
1. Add ```ECSVDefinition``` instead of specifying ECSV type and length of entity;
2. Native support of packing entities to another entity as internal entity or internal entity array;
3. Add support of list and maps of simple types (integer, float and boolean supported out-of-box);
4. Add special type of fields for dates with automatic formatting;
5. New storage functionality to hold entities data within simple text files with suport of basic operations: creating, getting, update and deletion;
6. Support of single of list referencing between entities even for other types.
7. Very simple handling with ```EntityHandler```;
8. Powerfull mechanism of quick initialization via ```FastStorageIgnition``` class;

Check out Wiki for more info!

## Copyright and license terms

Library distributed under terms of GNU LGPLv3 license.

**© Nebula Software 2015-2016**