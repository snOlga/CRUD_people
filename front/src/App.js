import Sprite from './Sprite.js';
import './App.css';
import useRef from 'react';

let currentZIndex = 0

function App() {
  return (
    <div className="App">
      <Sprite SpriteNumber={1} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={2} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={3} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={4} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={5} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={6} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={7} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={8} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={9} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={10} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={11} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={12} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={13} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={14} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={15} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={16} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />

      <Sprite SpriteNumber={17} zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />
    </div>
  );
}

export default App;
