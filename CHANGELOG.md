# Changelog

All notable changes to this plugin are documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Security

## [2.3.0] - 2026-10-11

### Added

- When creating exercises, multiple exercises (or all exercises of a series) can now be selected at once.

### Changed

- Bumped all dependencies
- Reworked the account manager to prevent breaking in a future IntelliJ version.
- PyCharm: Project generator now shows up, just like in IntelliJ.
- When creating an exercise, the course and series are now correctly remembered for the next exercise.

### Fixed

- Plugin is now compatible with the latest EAP versions of IntelliJ and PyCharm.
- Fixed a potential race condition with loading series.
- Plugin now sends the correct language of the user to Dodona.

[Unreleased]: https://github.com/thepieterdc/dodona-plugin-jetbrains/compare/v2.3.0...HEAD
[2.3.0]: https://github.com/thepieterdc/dodona-plugin-jetbrains/commits/v2.3.0
