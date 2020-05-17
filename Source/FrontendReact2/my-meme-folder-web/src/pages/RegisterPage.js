import React from "react";
import "./LoginRegisterPages.css"
import axios from "axios";
import {Link} from "react-router-dom";

export default class RegisterPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            email: "",
            password: "",
            passwordAgain: "",
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
        axios.post('/api/register', {
            username: this.state.username,
            email: this.state.email,
            password: this.state.password
        }).then(res => {
            window.location.href = "/login";
        }).catch(e => {
            const res = e.response;
            if (!res) {
                this.setState({errorMessage: `${e}`})
            } else if (res.status >= 400 && res.status < 500) {
                this.setState({errorMessage: res.data})
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
        if (this.state.password !== this.state.passwordAgain) {
            this.setState({errorMessage: "The two password fields must match."});
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
                        Register
                    </div>
                    <div>
                        <span className={this.state.errorMessage ? "login-form-error" : "login-form-prompt"}>
                            {this.state.errorMessage || "Please enter your credentials to register."}
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
                            type="text"
                            placeholder="Email address"
                            value={this.state.email}
                            onChange={this.setTextState.bind(this, "email")}
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
                            type="password"
                            placeholder="Repeat password"
                            value={this.state.passwordAgain}
                            onChange={this.setTextState.bind(this, "passwordAgain")}
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
                        <Link to="/login">Login with existing account</Link>
                    </div>
                </form>
            </main>
        )
    }
}