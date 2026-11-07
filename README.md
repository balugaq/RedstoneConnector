# RedstoneConnector

[![Build](https://github.com/balugaq/RedstoneConnector/actions/workflows/build.yml/badge.svg)](https://github.com/balugaq/RedstoneConnector/actions/workflows/build.yml)

> 新增可控连接器与红石远程控制器：用红石信号切换能源节点通断，远程隔空点亮红石执行器方块。

RedstoneConnector 是一个 [Slimefun4](https://github.com/SlimefunGuguProject/Slimefun4)（GuguProject 版）附属插件，为能源网络引入「可控连接器」，并附带一套红石远程触发器。

## 功能

### 可控连接器

一个受红石信号控制的能源网络连接器（`EnergyNetComponentType.CONNECTOR`）：

- **未通电**：铁块形态，不接入能源网络；
- **被红石充能**：方块变为红石块形态，接入能源网络，并即时更新周边网络拓扑；
- 两种形态间随红石信号实时切换，可用红石电路远程控制能源网络的通断。

配方（增强工作台）：红石火把 + 能源连接器（Slimefun）竖排。

### 红石远程控制器 + 红石远程执行器

配合实现跨距离的红石信号远程触发：

1. 手持**红石远程控制器**右键点击**红石远程执行器**完成绑定；
2. 之后在同一世界内、距绑定位置 60 格以内的任意位置，手持控制器右键；
3. 绑定的执行器方块会临时变为红石块（发出强充能信号），1 秒后自动恢复。

配方（增强工作台）：

- 红石远程控制器：中继器 / 末影珍珠 ×2 / 红石粉
- 红石远程执行器：末影之眼 / 红石火把 / 铁块 / 红石粉 / 末影珍珠

以上物品均位于 Slimefun 指南中的「可控连接器」分类下。

## 前置

- [Slimefun4](https://github.com/SlimefunGuguProject/Slimefun4)（必装，`depend: [Slimefun]`）
- Paper/Spigot 服务端（`api-version: 1.14` 及以上）

## 安装

将 `RedstoneConnector v<version>.jar` 放入服务端 `plugins/` 目录，重启服务端即可。

## 构建

```bash
./gradlew clean build
```

产物：`build/libs/RedstoneConnector v<version>.jar`。构建需要 Java 17+。

## 兼容性

CI 在 MC `1.20.1` 与最新 `26.x` 的 Paper 服务端上做启动兼容性测试（见 `.github/workflows/test-compatibility.yml`）。

## 反馈

问题请提交至 [Issues](https://github.com/balugaq/RedstoneConnector/issues)。

## 许可

原插件作者 [zimzaza4](https://github.com/zimzaza4/RedstoneConnector)。
