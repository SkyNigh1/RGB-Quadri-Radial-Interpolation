## 🧠 Quadri-Radial Interpolation System

SkymcDB uses a custom interpolation method called **Quadri-Radial Interpolation**, specifically designed to create natural and aesthetically pleasing gradients in Minecraft block textures.

### 🔍 How it works

Instead of interpolating between only two blocks in a straight line, the quadri-radial system operates on a **square grid** and uses its **two diagonals** as base references:

1. **The two diagonals** of the square (`\` and `/`) are filled with predefined color values or blocks.
2. For each row (or column) in the square, the algorithm takes:
   - One point from the left diagonal (`\`)
   - One point from the right diagonal (`/`)
3. It then applies a **linear interpolation** between those two points **across the row**, generating smooth transitions.

This method ensures that:
- The center of the square blends naturally from all corners
- Transitions follow curved, more organic paths instead of linear bands
- The result feels more “spatial” and balanced

### 📐 Visualization

A ---*--- B A and B are on opposite diagonals \ / * is the interpolated point on a line between A and B \ / \ / \ / X

Every pixel or block in the interpolation square is computed from a combination of diagonally aligned values, allowing smooth blending in all four directions. This is especially useful for color maps, terrain blending, and noise gradients.

### 🧮 Use Cases

- Custom block gradients that don't feel "flat"
- Color interpolations that mimic real-world shading
- Any logic where 2D transition feels better than a simple line

---

> Quadri-radial interpolation is unique to SkymcDB and is designed specifically for Minecraft's blocky, grid-based environment. It provides a higher degree of control and realism compared to basic linear interpolation.
