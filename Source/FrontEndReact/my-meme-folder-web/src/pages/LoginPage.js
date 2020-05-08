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
            password: ""
        }
    }

    setTextState = (field, event) => {
        this.setState({[field]: event.target.value});
    }

    handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData();
        data.set("username", this.state.username);
        data.set("password", this.state.password);
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
        });
    }

    render() {
        return (
            <main>
                <form className="login-form" onSubmit={this.handleSubmit}>
                    <div className="login-form-title">
                        Login
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