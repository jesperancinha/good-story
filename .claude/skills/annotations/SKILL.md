---
name: annotations
description: Conventions for using annotations in all tests using Jupiter
---

## 1. Make sure to use `@param:Value` eveywhere @Value is declared for Kotlin classes and files only

Remember to run tests after replacing all of them

## 2. Make sure to use interpolation prefixes for the `@Value` annotation in Kotlin classes and files only

### Example 1 (applying to params)

Replace this:

```kotlin
class VirtualThreadsDemoApplication(
    val taskService: TaskService,
    @param:Value("\${org.jesperancinha.vtcc.tasks}") private val tasks: Int,
)
```

with this:

```kotlin
class VirtualThreadsDemoApplication(
    val taskService: TaskService,
    @param:Value($$"${org.jesperancinha.vtcc.tasks}") private val tasks: Int,
) 
```

### Example 2 (applying to fields, on lateinit vars

Replace this:

```kotlin
class BankCompanyLauncherOtherPropertiesKotlinTest {
    /**
     * Properties and environment variables
     */
    @Value($$"${environment}")
    private lateinit var environment: String
}
```

with this:

```kotlin
class BankCompanyLauncherOtherPropertiesKotlinTest {
    /**
     * Properties and environment variables
     */
    @field:Value($$"${environment}")
    private lateinit var environment: String
}
```

The same should be applied to annotation `@Autowired`, but only for non-test classes and only in Kotlin files.
The same should be applied to annotation `@JsonProperty`, `@JsonDeserialize`, `@JsonAlias`, `@LocalServerPort`, but only in Kotlin files.
Do not add `@Autowired`, or any form of it with a different target to any parameter. `@Autowired` usage should be removed from production code instead.

## 3. Checklist

[] Make sure there is no more use of `@Value` annotation being used directly
[] Make sure no `@param:Value` has been changed in a Java file
[] Check that all `@param:Value` annotations are used correctly
