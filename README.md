### ⚙️ Industrial Mod (Minecraft 1.7.10) (02.02.2026)

A custom industrial mod inspired by classic tech mods. I'm learning Java development by creating a complex production chain.

### ✅ Current progress:
- **Materials:** Industrial ore, blocks, and ingots.
- **Tools:** Industrial hammer (consumes durability when crafting).
- **Resources:** Metal plates (the first step to machines).
- **World:** Ore generation in the depths of the earth.

### 🛠 Today's plans:

- [ ] Final textures for new items.
- [ ] Adding a hammer impact sound.
- [ ] Full localization into Russian and English.

---
*Project in active development.*

## 📅 Iteration #2: Tools and Forging (03.02.2026)
*The second stage of development: the transition from transformation to semi-finished products.*

### What's added:
- **Tools:** Implemented `Industrial Hammer`.
- *Feature:* The item breaks, but remains in the crafting grid.
- *Sound:* Added the effect of striking an anvil during crafting.
- **Items:** Added `Industrial Plates`.
- **Recipes:** Configured shapeless forging (Ingot + Hammer = Plate).
- **Graphics:** Created custom 16x16 textures in a unified style.
- **Localization:** Added translations for all new items (RU/EN).

---
## 📅 Iteration #3: World Gen Balancing (03.02.2026)
*Optimization and preparation for large-scale industrialization.*

### Changes:
- **Ore Generation:** Rebalanced `Industrial Ore` spawn rates.
  - Reduced `chance` per chunk from 20 to 8 (matching Iron ore rarity).
  - Adjusted `veinSize` to 6 for better gameplay balance.
  - Set vertical spawn range (`minY/maxY`) to 4-48 levels.
- **Git & Docs:** Updated repository structure and added future development roadmap.
---

## 📅 Iteration #4: Metallurgy & Rubber Foundation (04.03.2026)
*The core framework for the initial industrialization stage is now complete. A full processing cycle for Copper and Tin has been implemented.*

### Changes:
*   **Resources:** Added Copper and Tin ores with world generation in the Overworld.
*   **Metallurgy:** Implemented Copper and Tin ingots with smelting mechanics.
*   **Processing:** Added Copper and Tin plates, crafted using the Industrial Hammer.
*   **Botany:** Integrated base blocks for the Rubber Tree (Log, Leaves, and Sapling) to prepare for the rubber production system.
*   **Compatibility:** Fully integrated Ore Dictionary for seamless interaction with other industrial mods (e.g., IC2, GregTech).