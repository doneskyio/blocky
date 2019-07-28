# Blocky

An open source template engine

## Example

Default template (named `default`):

```html
[template]
[placeholder name="body"]
<div>Default Body</div>
[/placeholder]
<html>
<head><title>Blocky Example</title></head>
<body>
[ref:placeholder name="body"]
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