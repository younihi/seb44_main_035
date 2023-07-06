import styled from "styled-components";
import { FaAppleWhole } from "react-icons/fa6";

const MainLogo = () => {
  return (
    <LogoBox>
      <Logo>
        <Font>냉 파 고</Font>
        <SubFont>우리집 냉장고 파먹기 솔루션</SubFont>
      </Logo>
      <SubFont>
        냉장고 속 식재료<Span>를 입력해주세요</Span>
        <FaAppleWhole />
      </SubFont>
    </LogoBox>
  );
};

export default MainLogo;

const LogoBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 20%;
`;

const Logo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 250px;
  height: 100px;
  border-radius: 50%;
  background-color: rgba(245, 241, 233, 1);
  margin-bottom: 30px;
`;
const Font = styled.p`
  font-weight: bold;
  font-size: 30px;
  color: rgba(61, 80, 103, 1);
`;
const SubFont = styled.p`
  font-weight: bold;
  color: rgba(61, 80, 103, 1);
`;
const Span = styled.span`
  font-weight: bold;
  color: rgba(128, 148, 172, 1);
`;
