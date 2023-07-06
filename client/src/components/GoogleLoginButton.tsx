import { GoogleLogin } from '@react-oauth/google';
import { GoogleOAuthProvider } from '@react-oauth/google';
import { useNavigate } from 'react-router-dom';

const googleClientId: string = import.meta.env.VITE_GOOGLE_CLIENT_ID_2 || '';

const GoogleLoginButton = () => {
  const clientId = googleClientId;
  const navigate = useNavigate();
  return (
    <>
      <GoogleOAuthProvider clientId={clientId}>
        <GoogleLogin
          onSuccess={(res) => {
            const token = res.credential;
            if (token) {
              localStorage.setItem('accessToken', token);
            }
          }}
          onError={() => {
            alert('로그인에 실패하였습니다.');
            navigate('/login');
          }}
        />
      </GoogleOAuthProvider>
    </>
  );
};

export default GoogleLoginButton;
