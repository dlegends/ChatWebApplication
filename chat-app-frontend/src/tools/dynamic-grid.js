import * as React from 'react';


const numItemsPerRow = 5;
const spaceBetweenItems = 20; // edit to increase/decrease space between items

const containerStyle = {
  display: "flex",
  flexWrap: "wrap",
  margin: `-${spaceBetweenItems * 0.5}px`
  // backgroundColor: "#ddd"
};

const itemStyle = {
  display: "block",
  flex: "none",
  width: `${100 / numItemsPerRow}%`,
  boxSizing: "border-box",
  padding: `${spaceBetweenItems * 0.5}px`
  // backgroundColor: "#ddd"
};

const cardStyle = {
  backgroundColor: "palevioletred",
  height: "50px",
  textAlign: "center",
};

const elements = [1, 2, 3, 4, 5, 6, 7, 8];

const Grid = () => (
  <div style={containerStyle}>
    {elements.map(elem => (
      <div style={itemStyle}>
        <div style={cardStyle}>{elem}</div>
      </div>
    ))}
  </div>
);
export default GridGenerator;