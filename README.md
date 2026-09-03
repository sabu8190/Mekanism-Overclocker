# ⚡ Mekanism Overclocker

![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-brightgreen.svg)
![Forge](https://img.shields.io/badge/Forge-47.3.0+-blue.svg)
![Mekanism](https://img.shields.io/badge/Mekanism-10.4.0+-orange.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

**Mekanism Overclocker** は、Mekanism のパイプ（Logistical Transporter, Mechanical Pipe, Boxed Pressurized Tube, Universal Cable）およびマシン本体（Auto-Eject）の搬入出レートを限界突破させ、**Torcherino や倍速環境下でもボトルネックなしに 1 Tick で超大量のアイテム・液体・ガス・エネルギーを一括輸送可能にするオーバークロック専用 MOD** です。

単体での使用はもちろん、軽量化 MOD **[Mekanism Optimizer](https://github.com/sabu8190/Mekanism-Optimizer)** と完全互換・自動協調するスマートな設計となっています。

---

## 🚀 主な機能

### 1. ⚡ 4大リソースの個別搬入出オーバークロック
アイテム・液体・化学物質（ガス・インフュージョン・顔料・スラリー）・エネルギーの 4 つのリソースそれぞれで、個別に ON/OFF および 1 Tick あたりの出し入れ回数を自由自在に設定できます。

- **📦 アイテム (Item)**: 1 Tick あたり最大 64〜512 スタック一括搬送
- **💧 液体 (Fluid)**: 1 Tick あたり最大 64〜512 回バースト転送
- **🧪 ガス・化学物質 (Chemical/Gas)**: 1 Tick あたり最大 64〜512 回バースト転送
- **⚡ エネルギー (Energy / FE / Joules)**: 1 Tick あたり最大 64〜512 回バースト送電

### 2. ⚡ 搬入（PULL 吸い出し）＆ 搬出（Auto-Eject 押し出し）のダブル高速化
- **パイプ側（PULL モード）**:
  - 物流トランスポーターのハードコード待機時間（10 Tick 遅延）を撤廃し、1 Tick で設定スタック数を一括吸い出し。
  - メカニカルパイプ、加圧チューブ、ユニバーサルケーブルも同様に 1 Tick 内でマルチバースト吸引。
- **マシン本体側（Auto-Eject 押し出し）**:
  - マシン本体の自動搬出も 1 Tick で設定回数まで連続押し出し。
  - スロットやタンクが空になった瞬間に即時終了するため、無駄な処理負荷はゼロです。

### 3. 🛡️ Mekanism Optimizer 自動協調・重複排除システム
- **単体導入時**:
  - 本 MOD 単体でも加速時の高負荷を耐え抜くための軽量化機能（$O(1)$ レシピキャッシュ、ゼロアロケーションスロット）が内包されており、単体でも快適に爆速動作します。
- **[Mekanism Optimizer](https://github.com/sabu8190/Mekanism-Optimizer) 併用時**:
  - `Mekanism Optimizer` を動的に検知し、Overclocker 側の重複する軽量化 Mixin を **自動的に完全停止（バイパス）** します。競合や二重実行のリスクなく、両方のメリットを最大限に引き出せます。

---

## ⚙️ コンフィグ設定 (`config/mekanism_overclocker.toml`)

ゲーム起動後に `config/mekanism_overclocker.toml` が生成され、自由に数値を調整可能です。

```toml
[Overclocking Tuning]

    # 📦 【アイテム】オーバークロック
    enableItemOverclock = true
    itemBurstPerTick = 64      # 1 Tick あたりの最大搬出入スタック数 (1〜512)
    itemEjectTickDelay = 0    # 搬出遅延 (0 = 毎 Tick 即時搬出)

    # 💧 【液体】オーバークロック
    enableFluidOverclock = true
    fluidBurstPerTick = 64     # 1 Tick あたりの液体バースト回数 (1〜512)

    # 🧪 【ガス・化学物質】オーバークロック (Gas / Infusion / Pigment / Slurry)
    enableChemicalOverclock = true
    chemicalBurstPerTick = 64  # 1 Tick あたりの化学物質バースト回数 (1〜512)

    # ⚡ 【エネルギー】オーバークロック (FE / Joules)
    enableEnergyOverclock = true
    energyBurstPerTick = 64    # 1 Tick あたりの電力バースト回数 (1〜512)
```

---

## 📦 導入方法

1. **[Releases ページ](https://github.com/sabu8190/Mekanism-Overclocker/releases)** から最新の `mekanism_overclocker-1.20.1-x.x.x.jar` をダウンロードします。
2. Minecraft の `mods` フォルダに配置します。
3. 必要環境:
   - **Minecraft**: `1.20.1`
   - **Forge**: `47.3.0` 以上 (47.4.21+ 推奨)
   - **Mekanism**: `10.4.0` 以上

---

## 🤝 関連プロジェクト

- **[Mekanism Optimizer](https://github.com/sabu8190/Mekanism-Optimizer)**: ゲームバランスを変えずにサーバー負荷とラグを極限まで削減する純粋最適化 MOD。併用推奨！

---

## 📄 ライセンス

本プロジェクトは **MIT License** のもとで公開されています。
