import { useState } from "react";
import styled from "styled-components";
import { FaXmark } from "react-icons/fa6";

const SearchModal = () => {
  const [isDeleteBtn, setIsDeleteBtn] = useState(true);
  const handleDeleteClick = () => {
    setIsDeleteBtn(false);
  };
  return (
    <>
      {isDeleteBtn && (
        <Modal>
          <ModalContent>
            <FaXmark onClick={handleDeleteClick} />
            <AddIngre></AddIngre>
            <AddBtn>재료추가</AddBtn>
          </ModalContent>
        </Modal>
      )}
    </>
  );
};

export default SearchModal;

const Modal = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1;
`;

const ModalContent = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  background-color: rgba(98, 104, 131, 1);
  width: 300px;
  height: 223px;
  border-radius: 30px;
`;
const AddIngre = styled.input`
  background-color: rgba(245, 241, 233, 1);
  margin: 30px;
  color: black;
  width: 208px;
  height: 56px;
  border-radius: 10px;
`;
const AddBtn = styled.button`
  background-color: rgba(209, 232, 238, 1);
  color: rgba(76, 83, 114, 1);
  width: 141px;
  height: 51px;
  border-radius: 10px;
  font-weight: bold;
`;
