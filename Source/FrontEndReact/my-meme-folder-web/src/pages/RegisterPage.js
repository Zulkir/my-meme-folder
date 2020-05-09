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
            passwordAgain: ""
        }
    }

    setTextState = (field, event) => {
        this.setState({[field]: event.target.value});
    }

    handleSubmit = (event) => {
        event.preventDefault();
        axios.post('/api/register', {
            username: this.state.username,
            email: this.state.email,
            password: this.state.password
        });
        // todo: then
    }

    render() {
        return (
            <main>
                <form className="login-form" onSubmit={this.handleSubmit}>
                    <div className="login-form-title">
                        Register
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