import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import HomePage from "./pages/HomePage";
import MyFolderPage from "./pages/MyFolderPage";
import LoginPage from "./pages/LoginPage";

class App extends React.Component {
    render() {
        return (
            <Router>
                <div className="App">
                    <header style={headerStyle}>
                        <div style={logoStyle}>My Meme Folder</div>
                    </header>
                    <nav style={navStyle}>
                        <Link to="/myfolder" style={menuItemStyle}>My Folder</Link>
                        <Link to="/" style={menuItemStyle}>Explore</Link>
                    </nav>
                    <Route exact path="/" >
                        <HomePage />
                    </Route>
                    <Route path="/login" >
                        <LoginPage />
                    </Route>
                    <Route path="/myfolder" >
                        <MyFolderPage />
                    </Route>
                </div>
            </Router>
        );
    }

}

const headerStyle = {
  width: '100%',
  textAlign: 'left',
  //display: 'inline-block',
  backgroundColor: '#820',
}

const logoStyle = {
  display: 'inline-block',
  color: '#fff',
  fontSize: '50px',
  fontStyle: 'oblique',
  fontWeight: 'bold',
  margin: '10px 20px',
  lineHeight: '80px',
}

const navStyle = {
  padding: '10px',
  fontSize: '18px',
  fontWeight: 'bold',
  color: '#fff',
  backgroundColor: '#820',
  borderWidth: '5px 0px 0px 0px',
  borderColor: '#410',
  borderStyle: 'solid',
}

const menuItemStyle = {
  padding: '10px 20px'
}

export default App;
