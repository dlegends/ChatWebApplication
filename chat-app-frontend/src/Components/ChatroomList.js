import React from 'react';

const numItemsPerRow = 5;
const spaceBetweenItems = 50; // edit to increase/decrease space between items
const marginPercentage = 3;

const containerStyle = {
  display: "flex",
  flexWrap: "wrap",
  height: '100%',
  // backgroundColor: "#ddd"
};

const itemStyle = {
  flex: `0 1 ${100 / numItemsPerRow - marginPercentage}%`,
  height: `${100 / numItemsPerRow - marginPercentage}%`,
  padding: `${spaceBetweenItems * 0.5}px`,
  boxSizing: 'border-box',
  margin: `${marginPercentage / numItemsPerRow}%`,
  maxWidth: `${100 / numItemsPerRow - marginPercentage}%`,
  borderRadius: '25px',
  border: '2px solid #73AD21',
  backgroundColor: 'white',
  // backgroundColor: "#ddd"
};

const titleStyle = {
  textAlign: "center",
  textOverflow: "ellipsis",
  overflowX: "hidden",
  display: 'block',
  overflowY: 'hidden',
};

function ChatroomBox({ chatroom, onClick }) {
  return (
    <button style={itemStyle} onClick={onClick}>
      <h2 style={titleStyle}>
        {chatroom.title}
      </h2>
    </button>
  )
}

export default function ChatroomList({ onClick, chatrooms }) {
  return (
    <div style={containerStyle}>
      {chatrooms.map(cr => (
        <ChatroomBox chatroom={cr} key={cr.id} onClick={() => onClick(cr.id)} />
      ))}
    </div>
  )
}