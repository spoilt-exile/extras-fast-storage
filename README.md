FastStorage
============

Simple data storage based on CSV

**Current version:** *3.0*

ECSV is an enhanced format based on CSV. Main killer-feature is basic array and key-value pairs supporting. This library providing default implementation of ECSV format reader and writer. Library also supports referencing between types of entities, including other entities in another, storage for entities in file and entity map for transfering it through network. It also supports easy registration of entity handlers by ignition service.

ECSV parser doesn't use any reflection or annotations, so it should be extremely fast. See examples of ECSV formatted output in test files. Also check out ExampleDirectory as example of ECSV entity implementation.

## New features and changes in 3.0
1. Add entity map featere: single object which main contain entities by keys. Support adding more than one entity by single key, in this case key will be delt as list. Entity map supports different type of contained entities but all of them should be supplied with handlers. Entity map designed for simple transfer entities over network. Check out Wiki page about it.
2. Add `getEntityType()` method for `ECSVAble` interface, each entity should return it's own global type. Global type is a simple identification string for type of entity. Type doesn't used during normal serialization, but should be used for entity map processing.
3. Remove `TYPE` filed type. Including type information in stream should be depend on serialization context.

Check out Wiki for more info!

## Copyright and license terms

Library distributed under terms of GNU LGPLv3 license.

**© Nebula Software 2015-2016**