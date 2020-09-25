import React, { useRef } from 'react';

import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Button from 'react-bootstrap/Button';


export default function ChatroomSearch({ onSearch }) {
  const titleRef = useRef('');
  return (
    <InputGroup style={{ height: '100%', width: 'auto' }}>
      <FormControl
        placeholder="Search"
        aria-label="Message"
        aria-describedby="basic-addon2"
        style={{ height: '100%' }}
        ref={titleRef}
      />
      <InputGroup.Append>
        <Button variant="outline-secondary"
          onClick={() => onSearch(titleRef.current.value)}>Search</Button>
      </InputGroup.Append>

    </InputGroup>
  )
}