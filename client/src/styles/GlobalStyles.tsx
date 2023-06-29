import { createGlobalStyle } from 'styled-components';
import { normalize } from 'styled-normalize';
import { reset } from 'styled-reset';

const GlobalStyles = createGlobalStyle`
  ${reset}
  ${normalize}
  
  *, *::before, *::after {
    box-sizing: border-box;
  }

  body {
    font-family: "Helvetica", "Arial", sans-serif;
    line-height: 1.5;
  }
`;

export default GlobalStyles;
