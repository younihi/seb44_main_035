import { useState } from "react";
import { useNavigate } from "react-router-dom";
// import { useMutation, useQueryClient } from 'react-query';
// import axios from 'axios';
import SearchModal from "../components/SearchModal";
import MainLogo from "../components/MainLogo";
import styled from "styled-components";

import { FaPlus } from "react-icons/fa";
import { FaXmark } from "react-icons/fa6";

const MainPage = () => {
  const [isOpenAddIngre, setIsOpenAddIngre] = useState(false);
  const [isDeleteBtn, setIsDeleteBtn] = useState(true);
  const handleAddClick = () => {
    setIsOpenAddIngre(!isOpenAddIngre);
  };
  const handleDeleteClick = () => {
    setIsDeleteBtn(false);
  };
  const navigate = useNavigate();

  return (
    <StyledWrapper>
      <AppBox>
        <Header>
          <MainLogo />
        </Header>
        <Body>
          {/* 컴포넌트화 */}
          <GridContainer>
            {isDeleteBtn && (
              <IngreBtn>
                <Ingre>감자</Ingre>
                <DeleteBtn onClick={handleDeleteClick}>
                  <FaXmark />
                </DeleteBtn>
              </IngreBtn>
            )}

            <PlusBtn onClick={handleAddClick}>
              <FaPlus size="30" />
            </PlusBtn>
            {isOpenAddIngre && <SearchModal />}
          </GridContainer>
          <BottomBtn>
            <Btn onClick={() => navigate("/recipes")}>추천 레시피</Btn>
            <Btn> 선택 재료</Btn>
          </BottomBtn>
        </Body>
      </AppBox>
    </StyledWrapper>
  );
};

export default MainPage;
const BottomBtn = styled.div`
  display: flex;
  justify-content: center;
`;
const Btn = styled.button`
  width: 140px;
  height: 46px;
  background-color: rgba(100, 117, 138, 1);
  color: white;
  padding: 15px;
  margin: 30px;
  border-radius: 11px;
  border: none;
  font-weight: bold;
`;

const StyledWrapper = styled.main`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;
  /* margin: 0;
  padding: 0; */
  background-color: rgba(241, 241, 241, 0.5);
`;

const AppBox = styled.div`
  background-color: rgba(209, 232, 238, 1);
  max-width: 420px;
  width: 100%;
  height: 100%;
  position: relative;
`;

const Header = styled.header`
  width: 100%;
  height: 20%;
`;
const Body = styled.div`
  background-color: white;
  border-radius: 5% 5% 0 0;
  width: 100%;
  height: 80%;
  padding: 30px;
`;
const PlusBtn = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(238, 238, 238, 1);
  border-radius: 18%;
  width: 80px;
  height: 85px;
  border: none;
`;
const IngreBtn = styled.div`
  //상속하는 거 알아오기
  display: flex;
  align-items: center;
  flex-direction: column;

  background-color: rgba(238, 238, 238, 1);
  border-radius: 18%;
  width: 80px;
  height: 85px;
`;
const Ingre = styled.div`
  font-weight: 600;
  font-size: 20px;
  margin-top: 25px;
`;

const DeleteBtn = styled.button`
  // 자식 버튼으로 만들기
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
  background-color: rgba(198, 197, 197, 1);
  border-radius: 0;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  width: 80px;
  height: 29px;
  border: none;
`;

const GridContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: repeat(5, 1fr);
  grid-gap: 10px;
`;
