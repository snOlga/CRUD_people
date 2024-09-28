import SpriteContainer from './SpriteContainer.js';
import LeftMenuContainer from './LeftMenuContainer.js';
import './styles/App.css';

let currentZIndex = 0

function App() {
  return (
    <div className="App">
      <link rel="preconnect" href="https://fonts.googleapis.com" />
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
      <link href="https://fonts.googleapis.com/css2?family=Tiny5&display=swap" rel="stylesheet" />
      <LeftMenuContainer />
      <SpriteContainer zIndexFromMain={currentZIndex++}
        startX={Math.random() * window.innerWidth}
        startY={Math.random() * window.innerHeight} />
    </div>
  );
}

export default App;
