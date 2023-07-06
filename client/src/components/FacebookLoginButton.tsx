import { FacebookProvider, LoginButton } from 'react-facebook';
import styled from 'styled-components';

const StyledLoginButton = styled(LoginButton)`
  && {
    background-color: #4267b2;
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
  }
`;

const facebookAppId: string = import.meta.env.VITE_FACEBOOK_APP_ID || '';

function FacebookLoginButton() {
  console.log(facebookAppId);
  function handleSuccess(response: any) {
    console.log(response);
  }

  function handleError(error: any) {
    console.log(error);
  }

  return (
    <FacebookProvider appId={facebookAppId}>
      <StyledLoginButton
        scope='email'
        onError={handleError}
        onSuccess={handleSuccess}
      >
        Login Facebook
      </StyledLoginButton>
    </FacebookProvider>
  );
}

export default FacebookLoginButton;
