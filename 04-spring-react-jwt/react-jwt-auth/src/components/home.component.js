import React, { useState, useEffect } from 'react'
import UserService from '../services/user.service'

const Home = () => {
  const [content, setContent] = useState()
  useEffect(() => {
    UserService.getPublicContent().then((response) => {
      // console.log(response.data)
      setContent(response.data)
    })
  }, [])
  return (
    <div className="container">
      <header className="jumbotron">
        <h3>{content}</h3>
      </header>
    </div>
  )
}

export default Home
