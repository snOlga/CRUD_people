import Sprite from './Sprite.js';
import SpriteContainer from './SpriteContainer.js';
import './styles/App.css';
import useRef from 'react';

let currentZIndex = 0

function App() {
  return (
    <div className="App">
      <SpriteContainer zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />
    </div>
  );
}

export default App;
