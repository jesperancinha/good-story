---
name: kotlin patterns
description: Conventions for using Kotlin
---

## 1. Remove usage of `!!` operator

The `!!` operator should be avoided in Kotlin as it can lead to `NullPointerException` at runtime. Instead, use safe calls (`?.`) or the `let` scope function to handle nullable values more gracefully.

## 2. Remove the safe call operator (`?.`) when possible

Using the safe call operator (`?.`) is a good practice, but it should be used judiciously.
It makes no sense to use this operator when the value is guaranteed to be non-null, as it can lead to unnecessary null checks and reduce code readability.

## 3. Avoid using `var` when possible

In Kotlin, it is recommended to use `val` instead of `var` whenever possible. This helps to make the code more readable and maintainable, as it makes it clear that the value of a variable is not expected to change.

## 4. Use Duration overload when possible

### Example 1

When using the `delay` function, it is recommended to use the overload that accepts a `Duration` parameter instead of a `Long` parameter. This makes the code more readable and easier to understand, as it clearly indicates the intended duration of the delay.

replace this:

```kotlin
delay(100)
```

with this:

```kotlin
delay(100.milliseconds)
```

Add import: `import kotlin.time.Duration.Companion.milliseconds`

## 5. Remove all code signatures

Code does not need to be signed. The code signature used to have some value, but in current times, the commit already has a signature.
Old code may contain signatures that look like this:

```kotlin
/**
 * Created by joao on 28-4-16.
 */
```

They should all be removed, as they are not needed and add no value to the code.

## 6. Checklist

[ ] The code does not use the `!!` operator.
[ ] The code does not use the safe call operator (`?.`) when the value is guaranteed to be non-null.
[ ] The code uses `val` instead of `var` whenever possible.
[ ] The code uses the `Duration` overload when using the `delay` function.
[ ] The code does not contain any code signatures.