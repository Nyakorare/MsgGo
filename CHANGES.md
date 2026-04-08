# MsgGo Change Log (2026-04-07)

This file summarizes all implemented changes in this update batch.

## 1) Send Delay Updated

- Increased maximum send delay from `8000ms (8s)` to `15000ms (15s)`.
- Kept the old constant commented out as requested.

### Updated file

- `app/src/main/java/top/yztz/msggo/data/Settings.java`

---

## 2) Random Send Delay (Min/Max) Added

Replaced the old fixed random range logic with configurable min/max values.

- Added settings for random delay minimum and maximum.
- Sending now uses a random delay in `[randomDelayMin, randomDelayMax]`.
- Old random-delay code path was kept commented out as requested.

### Validation behavior

- `randomDelayMin` must be:
  - `>=` current base send delay
  - `<= 15s`
- `randomDelayMax` must be:
  - `>= randomDelayMin`
  - `<= 15s`
- If base delay changes, stored random values are clamped to remain valid.

### New preference keys

- `random_delay_min_v1`
- `random_delay_max_v1`

### Updated files

- `app/src/main/java/top/yztz/msggo/data/SettingManager.java`
- `app/src/main/java/top/yztz/msggo/activities/SendingActivity.java`
- `app/src/main/java/top/yztz/msggo/fragments/SettingFrag.java`
- `app/src/main/res/layout/layout_setting.xml`
- `app/src/main/res/values/strings.xml`
- `app/src/main/res/values-zh-rCN/strings.xml`

---

## 3) Excel Row Limit Made Configurable

Changed Excel row handling from a fixed limit to a configurable setting.

- Global hard cap changed from `200` to `800`.
- Added user-configurable row limit with default `200`.
- Configurable value is validated in range `1..800`.
- Import validation now uses the user-configured limit.
- Old fixed-row-limit check was kept commented out as requested.
- Error message now shows the configured limit value.

### New preference key

- `excel_send_row_count_v1`

### Updated files

- `app/src/main/java/top/yztz/msggo/data/Settings.java`
- `app/src/main/java/top/yztz/msggo/data/SettingManager.java`
- `app/src/main/java/top/yztz/msggo/util/ExcelReader.java`
- `app/src/main/java/top/yztz/msggo/activities/MainActivity.java`
- `app/src/main/java/top/yztz/msggo/fragments/SettingFrag.java`
- `app/src/main/res/layout/layout_setting.xml`
- `app/src/main/res/values/strings.xml`
- `app/src/main/res/values-zh-rCN/strings.xml`

---

## 4) Settings UI Expanded

Added new rows in Settings:

- Random Delay Min
- Random Delay Max
- Excel Row Send Limit

Each row opens numeric input and saves validated values.

### Updated files

- `app/src/main/res/layout/layout_setting.xml`
- `app/src/main/java/top/yztz/msggo/fragments/SettingFrag.java`

---

## 5) Update Check URL Changed

Settings -> Check for updates now points to:

- `https://github.com/Nyakorare/MsgGo/releases`

### Updated file

- `app/src/main/java/top/yztz/msggo/fragments/SettingFrag.java`

---

## 6) GitHub Workflow Branch Target Changed

Updated workflow checkout to use your feature branch explicitly:

- `experiment/feature-idea`

### Updated files

- `.github/workflows/release.yml`
- `.github/workflows/nightly.yml`

---

## 7) About Page Credit Added

Added a new line below `Made with ❤️ by Yztz`:

- `Specially modified by Glenn`

### Updated files

- `app/src/main/res/layout/layout_about.xml`
- `app/src/main/res/values/strings.xml`
- `app/src/main/res/values-zh-rCN/strings.xml`

---

## 8) Localization/String Updates

Added and updated EN/CN strings for:

- New settings labels
- New setting summaries
- Numeric input hints
- Validation error messages
- About page “Specially modified by Glenn” text

### Updated files

- `app/src/main/res/values/strings.xml`
- `app/src/main/res/values-zh-rCN/strings.xml`

---

## 9) Validation and Verification Notes

- Edited files were checked with IDE lints: no new lint errors reported.
- Local Gradle build was not fully run in this environment due to missing Java configuration:
  - `JAVA_HOME is not set and no 'java' command could be found in your PATH.`
