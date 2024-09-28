import SpriteContainer from './SpriteContainer.js';
import './styles/App.css';

let currentZIndex = 0

function SpriteField() {
  return (
    <div className='SpriteField'>
      <SpriteContainer zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />
    </div>
  );
}

export default SpriteField;
