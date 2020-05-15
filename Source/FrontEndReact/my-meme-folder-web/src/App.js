import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import HomePage from "./pages/HomePage";
import MyFolderPage from "./pages/MyFolderPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import userService from "./services/UserService";
import LogoutPage from "./pages/LogoutPage";

class App extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            userInfo: null
        };
    }

    componentDidMount() {
        userService.subscribeToChange(u => this.setState({userInfo: u}));
        userService.refreshUserInfo();
    }

    render() {
        return (
            <Router>
                <div className="App">
                    <header style={headerStyle}>
                        <div style={logoStyle}>My Meme Folder</div>
                        {
                            this.state.userInfo ? (
                                <div style={userInfoStyle}>
                                    Welcome, {this.state.userInfo.username}! <br/>
                                    <Link to={"/logout"}>Logout</Link>
                                </div>
                            ) : (
                                <div style={userInfoStyle}>
                                    Welcome! Please login to edit your folder. <br/>
                                    <Link to={"/login"}>Login</Link> <br/>
                                    <Link to={"/register"}>Register</Link>
                                </div>
                            )
                        }
                    </header>
                    <nav style={navStyle}>
                        <Link to="/folder" style={menuItemStyle}>My Folder</Link>
                        <Link to="/" style={menuItemStyle}>Explore</Link>
                    </nav>
                    <Route exact path="/" component={HomePage} />
                    <Route path="/login" component={LoginPage} />
                    <Route path="/logout" component={LogoutPage} />
                    <Route path="/register" component={RegisterPage} />
                    <Route path="/folder/:username" component={MyFolderPage} />
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

const userInfoStyle = {
    padding: '10px',

    float: 'right',
    textAlign: 'right'
}

const navStyle = {
    display: 'none',

    padding: '10px',
    fontSize: '18px',
    fontWeight: 'bold',
    color: '#fff',
    backgroundColor: '#820',
    borderWidth: '5px 0px 0px 0px',
    borderColor: '#410',
    borderStyle: 'solid'
}

const menuItemStyle = {
  padding: '10px 20px'
}

export default App;
