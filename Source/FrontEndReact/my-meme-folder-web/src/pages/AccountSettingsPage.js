import React from "react";
import "./LoginRegisterPages.css"
import {Link} from "react-router-dom";
import axios from "axios";
import userService from "../services/UserService.js";

export default class AccountSettingsPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            email: "",
            folderIsPublic: false,
            imagesArePublic: false,
            errorMessage: null
        }
    }

    userInfoHandle = u => {
        if (u)
            this.setState({
                username: u.username,
                email: u.email,
                folderIsPublic: u.folderIsPublic,
                imagesArePublic: u.imagesArePublic
            });
        else
            this.setState({
                username: "",
                email: "",
                folderIsPublic: false,
                imagesArePublic: false,
            });
    }

    componentDidMount() {
        userService.subscribeToChange(this.userInfoHandle);
        this.userInfoHandle(userService.getUserInfo());
    }

    componentWillUnmount() {
        userService.unsubscribeFromChange(this.userInfoHandle);
    }

    setTextState = (field, event) => {
        this.setState({[field]: event.target.value});
    }

    setCheckboxState = (field, event) => {
        this.setState({[field]: event.target.checked});
    }

    handleSubmit = (event) => {
        event.preventDefault();
        if (!this.validateInput())
            return;
        axios.put('/api/user-info/', {
            username: this.state.username,
            email: this.state.email,
            folderIsPublic: this.state.folderIsPublic,
            imagesArePublic: this.state.imagesArePublic
        }).then(res => {
            userService.refreshUserInfo();
            // todo: to history.push with username changed notification
            window.location.href = `/folder/${this.state.username}/`;
        }).catch(e => {
            let res = e.response;
            if (res)
                this.setState({errorMessage: `Error ${res.status}: ${res.statusText}`});
            else
                this.setState({errorMessage: e});
        });
    }

    validateInput = () => {
        if (!this.state.username || this.state.username.length === 0) {
            this.setState({errorMessage: "Username cannot be empty."});
            return false;
        }
        if (!this.state.email || this.state.email.length === 0) {
            this.setState({errorMessage: "Email cannot be empty."});
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
                        Account settings
                    </div>
                    <div>
                        <span className={this.state.errorMessage ? "login-form-error" : "login-form-prompt"}>
                            {this.state.errorMessage || ""}
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
                        <label>
                            <input
                                type="checkbox"
                                checked={this.state.folderIsPublic}
                                //value={this.state.folderIsPublic}
                                onChange={this.setCheckboxState.bind(this, "folderIsPublic")}
                            />
                            Make the folder public
                        </label>
                    </div>
                    <div>
                        <label>
                            <input
                                type="checkbox"
                                checked={this.state.imagesArePublic}
                                //value={this.state.folderIsPublic}
                                onChange={this.setCheckboxState.bind(this, "imagesArePublic")}
                            />
                            Make the images public
                        </label>
                    </div>
                    <div>
                        <input
                            className="login-form-submit"
                            type="submit"
                            value="Submit"
                        />
                    </div>
                    <div>
                        <Link to="/change-password">Change password</Link>
                    </div>
                </form>
            </main>
        )
    }
}