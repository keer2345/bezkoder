import React, { useState, useEffect } from 'react'

const Tutorial = () => {
  const [currentTutorial, setCurrentTutorial] = useState(null)

  useEffect(() => {
    getTutorial()
  }, [])

  return (
    <div>
      {currentTutorial ? (
        <div className="edit-form">
          <h4>Tutorial</h4>
          <form>
            <div className="form-group">
              <label htmlFor="title">Title</label>
              <input
                type="text"
                className="form-control"
                id="title"
                value={currentTutorial.title}
              />
            </div>
          </form>
        </div>
      ) : (
        <div>
          <br />
          <p>Please click on a Tutorial...</p>
        </div>
      )}
    </div>
  )
}

export default Tutorial
