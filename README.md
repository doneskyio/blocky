[![Build Status](https://travis-ci.com/doneskyio/blocky.svg?branch=master)](https://travis-ci.com/doneskyio/blocky) [![codecov](https://codecov.io/gh/doneskyio/blocky/branch/master/graph/badge.svg)](https://codecov.io/gh/doneskyio/blocky) ![Maven Central](https://img.shields.io/maven-central/v/io.donesky.blocky/blocky)



# Blocky

An open source template engine

## Example

Default template (named `default`):

```html
[template]
[placeholder name="body"] [!-- Default placeholders are optional --]
<div>Default Body</div>
[/placeholder]
<html>
<head><title>Blocky Example</title></head>
<body>
[ref:placeholder name="body"] [[-- Escaping [] --]
</body>
</html>
[/template]
```

Template leveraging the default template (named `some-template`):
```html
[template parent="default"]
[placeholder name="body"]
<div>Some other body: [ctx:somevariable]</div>
[/placeholder]
[/template]
```

Rendering template:
```kotlin
val template = Blocky["some-template"]
template.write(Context(mapOf("somevariable" to "Oh yea")), outputStream)
```

Expected output:

```html
<html>
<head><title>Blocky Example</title></head>
<body>
<div>Some other body: Oh yea</div>  Escaping []
</body>
</html>
```

Want more examples?  Visit the tests: 
https://github.com/doneskyio/blocky/tree/master/blocky/src/test/kotlin/blocky