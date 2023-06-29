import ReactDOM from 'react-dom/client';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import GlobalStyles from './styles/GlobalStyles';

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <BrowserRouter>
    <RecoilRoot>
      <GlobalStyles />
      <App />
    </RecoilRoot>
  </BrowserRouter>
);
