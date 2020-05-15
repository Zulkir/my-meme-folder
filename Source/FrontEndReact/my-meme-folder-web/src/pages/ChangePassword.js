import React from "react";
import "./LoginRegisterPages.css"
import axios from "axios";
import {Link} from "react-router-dom";

export default class ChangePasswordPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            password: "",
            newPassword: "",
            newPasswordAgain: "",
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
        axios.put('/api/change-password', {
            password: this.state.password,
            newPassword: this.state.newPassword
        }).then(res => {
            window.location.href = "/login";
        }).catch(e => {
            const res = e.response;
            if (!res) {
                this.setState({errorMessage: `${e}`})
            } else if (res.status >= 400 && res.status < 500) {
                this.setState({errorMessage: res.data.message})
            } else {
                this.setState({errorMessage: `Error ${res.status}: ${res.statusText}`});
            }
        });
    }

    validateInput = () => {
        if (!this.state.password || this.state.password.length === 0) {
            this.setState({errorMessage: "Current password cannot be empty."});
            return false;
        }
        if (!this.state.newPassword || this.state.newPassword.length === 0) {
            this.setState({errorMessage: "New password cannot be empty."});
            return false;
        }
        if (this.state.newPassword !== this.state.newPasswordAgain) {
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
                        Change password
                    </div>
                    <div>
                        <span className={this.state.errorMessage ? "login-form-error" : "login-form-prompt"}>
                            {this.state.errorMessage || (
                                <React.Fragment>
                                    Please enter your current password and <br/> &nbsp; type new one twice to change it.
                                </React.Fragment>
                            )}
                        </span>
                    </div>
                    <div>
                        <input
                            type="password"
                            placeholder="Current password"
                            value={this.state.password}
                            onChange={this.setTextState.bind(this, "password")}
                        />
                    </div>
                    <div>
                        <input
                            type="password"
                            placeholder="New password"
                            value={this.state.newPassword}
                            onChange={this.setTextState.bind(this, "newPassword")}
                        />
                    </div>
                    <div>
                        <input
                            type="password"
                            placeholder="Repeat new password"
                            value={this.state.newPasswordAgain}
                            onChange={this.setTextState.bind(this, "newPasswordAgain")}
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
                        <Link to="/account-settings">Account settings</Link>
                    </div>
                </form>
            </main>
        )
    }
}