# AGENTS.md — RedstoneConnector 操作契约

> 本文件是 AI 编程助手与本仓库开发者的**操作契约**。当本文与代码冲突时，以代码为准并在回复中指出。

## TL;DR

1. 本仓库是 **Minecraft Paper + Slimefun4（GuguProject 版）附属插件**（Java 17 / Gradle Kotlin DSL），所有改动必须通过 `./gradlew clean build` 验收后才能提交。
2. **禁止**改动物品/Slimefun ID（`Items.java` 中的 `INACTIVE_REDSTONE_CONNECTOR`、`REDSTONE_CONNECTOR`、`REDSTONE_REMOTE_CONTROLLER`、`REMOTE_EXECUTE_BLOCK`，改了即损坏玩家方块数据）、**禁止** `git push -f`、**禁止** `System.out.println`。
3. 本地直接在 `master` 分支开发（个人仓库），提交信息用 Conventional Commits（`feat:`/`fix:`/`docs:`/`chore:`）。

## 1. 项目身份

| 维度 | 事实 |
| --- | --- |
| 定位 | Slimefun4 附属：可控能源网络连接器 + 红石远程触发器 |
| 入口类 | `me.zimzaza4.redstoneconnector.Main`（`extends JavaPlugin implements SlimefunAddon`） |
| 版本目标 | Java 17（`compileJava { options.release = 17 }`，无 toolchain）、paper-api 1.20.1、Slimefun4 2025.1（JitPack） |
| 上游 | `zimzaza4/RedstoneConnector`（fork，`.github/pull.yml` 配置了上游同步） |
| `plugin.yml` | `depend: [Slimefun]`，`api-version: 1.14`，`version: ${version}` 由 Gradle `processResources` 注入 |

## 2. 包地图（全项目仅 7 个类）

| 类 | 职责 |
| --- | --- |
| `Main` | 入口；`onEnable()` 注册全部 4 个 Slimefun 物品；`key()` 生成 NamespacedKey |
| `Items` | ItemGroup「可控连接器」与 4 个 `SlimefunItemStack` 常量（ID 一经发布不可改） |
| `RedstoneConnector` | 基类，`BlockTicker`（synchronized）里根据 `getBlockPower()` 在两种形态间切换：充能→`REDSTONE_CONNECTOR`+红石块；断电→`INACTIVE_REDSTONE_CONNECTOR`+铁块；每次切换后 `updateAllNetworks` |
| `ActiveRedstoneConnector` | 激活态，`EnergyNetComponent`（`CONNECTOR` 类型，容量 0），掉落物为未激活态 |
| `InactiveRedstoneConnector` | 未激活态（可合成） |
| `remote.RemoteController` | 手持物品（不可放置）。`ItemUseHandler`：右键 Slimefun 方块 `REMOTE_EXECUTE_BLOCK` → 用 PDC `location` 键绑定 `world;x;y;z`；已绑定时右键 → 同世界且 ≤60 格（构造参数）则远程把执行器方块变红石块，20 tick 后恢复 |
| `remote.RemoteExecuteBlock` | 远程执行目标方块（可放置），自身无逻辑，由控制器驱动 |

## 3. 构建与依赖

- **构建**：`./gradlew clean build`；无 shade 需求（依赖全 `compileOnly`），产物 `build/libs/RedstoneConnector v<version>.jar`。
- **依赖**（见 `gradle/libs.versions.toml`，版本变更在注释里记录原版本）：
  - `io.papermc.paper:paper-api:1.20.1`（repo.papermc.io）——原 Maven 为 spigot-api 1.17.1；
  - `com.github.SlimefunGuguProject:Slimefun4:2025.1`（jitpack.io）——原为 `b39097e015`（JitPack 远端 404，2026-11-07 升级）。
- **版本号单一来源**：`gradle.properties` 的 `version=`；`build.yml` 的产物上传与 `release.yml` 的 tag 均从 Gradle/该文件取版本。改版本只动 `gradle.properties`。
- `plugin.yml` 的 `version: ${version}` 是 Gradle 占位符，**不要**改回 `${project.version}`（那是 Maven 的）。

## 4. CI

| Workflow | 触发 | 要点 |
| --- | --- | --- |
| `build.yml` | push/PR → `master` | JDK 17，`./gradlew test` + `clean build`，上传产物；`[ci skip]` 可跳过 |
| `release.yml` | `gradle.properties`/`build.gradle.kts` 变更推送 | 版本号打 tag `v<version>`，tag 已存在则跳过 |
| `test-compatibility.yml` | push/PR，矩阵 `1.20.1,26.2` | 下载 Slimefun4（鬼斩资源站构建源）启动 Paper 验证 Enabling/Disabling 顺序 |
| `pull.yml` | 上游同步 | upstream `zimzaza4/RedstoneConnector@master` |

## 5. 红线

1. **禁止改动物品/Slimefun ID**（见 TL;DR 第 2 条）；`ActiveRedstoneConnector` 的形态切换靠 `StorageCacheUtils.setData("id", ...)` 写 ID 实现，ID 变更会直接毁档。
2. **禁止未构建验证就提交**：改动必须过 `./gradlew clean build`。
3. **禁止 `git push -f` / 改写历史**。
4. **禁止在 `plugin.yml` 使用 Maven 占位符** `${project.version}`。
5. Windows 上提交 `gradlew` 后必须确认 git mode 为 `100755`（`git update-index --chmod=+x gradlew`），否则 CI exit 126。

## 6. 已知约束 / 坑

- `RemoteController` 的绑定校验：绑定阶段无距离限制，使用阶段有 60 格 + 同世界限制（`Bukkit.getWorld(name)`，按世界名匹配）。
- `Items.ACTIVE_CONNECTOR` 的展示名是字面量 `"wtf"`（隐藏物品，指南中不可见），如需调整展示名不影响存档。
- 模板来源的 `config.yml` 仅含 `auto-update`，插件实际未读取它。
