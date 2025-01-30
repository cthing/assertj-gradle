# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [unreleased]

### Added

- Methods on `GradleProjectAssert` for "satisfying" assertions on extensions, configurations and tasks
  (e.g. `hasConfigurationSatisfying`)
- `GradleAssertFactories` to access Gradle types contained within a
  [Provider](https://docs.gradle.org/current/javadoc/org/gradle/api/provider/Provider.html)
- Assertions for the following Gradle objects:
  - [Configuration](https://docs.gradle.org/current/javadoc/org/gradle/api/artifacts/Configuration.html)
  - [Directory](https://docs.gradle.org/current/javadoc/org/gradle/api/file/Directory.html)
  - [DirectoryProperty](https://docs.gradle.org/current/javadoc/org/gradle/api/file/DirectoryProperty.html)
  - [FileCollection](https://docs.gradle.org/current/javadoc/org/gradle/api/file/FileCollection.html)
  - [RegularFile](https://docs.gradle.org/current/javadoc/org/gradle/api/file/RegularFile.html)
  - [RegularFileProperty](https://docs.gradle.org/current/javadoc/org/gradle/api/file/RegularFileProperty.html)
  - [Task](https://docs.gradle.org/current/javadoc/org/gradle/api/Task.html)

### Changed

- The signatures of the following `GradleProjectAssert` methods now require at least one parameter to be specified:
  - `hasExtension`
  - `doesNotHaveExtension`
  - `hasConfiguration`
  - `doesNotHaveConfiguration`
  - `hasPlugin`
  - `doesNotHavePlugin`
  - `hasTask`
  - `doesNotHaveTask`

## [2.0.0] - 2025-01-26

### Added

- Gradle [Provider](https://docs.gradle.org/current/javadoc/org/gradle/api/provider/Provider.html) assertions
- `GradleAssertions` class provides a single import for all Gradle assertions

### Changed

- JSpecify is now required as part of the plugin's API

## [1.0.0] - 2024-12-24

### Added

- First release

[unreleased]: https://github.com/cthing/assertj-gradle/compare/2.0.0...HEAD
[2.0.0]: https://github.com/cthing/assertj-gradle/releases/tag/2.0.0
[1.0.0]: https://github.com/cthing/assertj-gradle/releases/tag/1.0.0
