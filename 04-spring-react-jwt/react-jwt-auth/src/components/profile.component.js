import React from 'react'
import AuthService from '../services/auth.service'

const Profile = () => {
  const currentUser = AuthService.getCurrentUser()
  return (
    <div className="container">
      <div>
        <header className="jumbotron">
          <h3>
            <strong>{currentUser.username}</strong>
          </h3>
        </header>
        <p>
          <strong>Token:</strong> {currentUser.token.substring(0, 20)} ...{' '}
          {currentUser.token.substr(currentUser.token.length - 20)}
        </p>
        <p>
          <strong>Id:</strong> {currentUser.id}
        </p>
        <p>
          <strong>Email:</strong> {currentUser.email}
        </p>
        <div>
          <strong>Authorities:</strong>
          <ul>
            {currentUser.roles &&
              currentUser.roles.map((role, index) => (
                <li key={index}>{role}</li>
              ))}
          </ul>
        </div>
      </div>
    </div>
  )
}

export default Profile
