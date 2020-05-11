import React from "react";
import "./LoginRegisterPages.css"
import {Link} from "react-router-dom";
import axios from "axios";
import querystring from 'querystring';

export default class LoginPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            errorMessage: null
        }
    }

    setTextState = (field, event) => {
        this.setState({[field]: event.target.value});
    }

    handleSubmit = (event) => {
        event.preventDefault();
        if (!this.validateInput())
            return;
        axios({
            url: '/api/login',
            method: 'POST',
            headers: {
                'content-type': 'application/x-www-form-urlencoded'
            },
            data: querystring.stringify({
                username: this.state.username,
                password: this.state.password
            })
        }).then(res => {
            // todo: to history.push with username changed notification
            window.location.href = `/folder/${this.state.username}/`;
        }).catch(e => {
            let res = e.response;
            if (res.status === 403) {
                this.setState({errorMessage: "Invalid username or password"})
            } else {
                console.log(res);
                this.setState({errorMessage: `Error ${res.status}: ${res.statusText}`});
            }
        });
    }

    validateInput = () => {
        if (!this.state.username || this.state.username.length === 0) {
            this.setState({errorMessage: "Username cannot be empty."});
            return false;
        }
        if (!this.state.password || this.state.password.length === 0) {
            this.setState({errorMessage: "Password cannot be empty."});
            return false;
        }
        if (this.state.errorMessage) {
            this.setState({errorMessage: null});
        }
        return true;
    }

    render() {
        return (
            <main>
                <form className="login-form" onSubmit={this.handleSubmit}>
                    <div className="login-form-title">
                        Login
                    </div>
                    <div>
                        <span className={this.state.errorMessage ? "login-form-error" : "login-form-prompt"}>
                            {this.state.errorMessage || "Please enter your credentials to login."}
                        </span>
                    </div>
                    <div>
                        <input
                            type="text"
                            placeholder="User name"
                            value={this.state.username}
                            onChange={this.setTextState.bind(this, "username")}
                        />
                    </div>
                    <div>
                        <input
                            type="password"
                            placeholder="Password"
                            value={this.state.password}
                            onChange={this.setTextState.bind(this, "password")}
                        />
                    </div>
                    <div>
                        <input
                            className="login-form-submit"
                            type="submit"
                            value="Submit"
                        />
                    </div>
                    <div>
                        <Link to="/forgot-password">Forgot password?</Link>
                    </div>
                    <div>
                        <Link to="/register">Register</Link>
                    </div>
                </form>
            </main>
        )
    }
}