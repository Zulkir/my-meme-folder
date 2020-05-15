import React from "react";
import "./LoginRegisterPages.css"
import axios from "axios";
import querystring from 'querystring';
import userService from "../services/UserService.js";

export default class LogoutPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: null
        }
    }

    userInfoHandle = u => this.setState({username: u ? u.username : null})

    componentDidMount() {
        userService.subscribeToChange(this.userInfoHandle);
        this.userInfoHandle(userService.getUserInfo());
    }

    componentWillUnmount() {
        userService.unsubscribeFromChange(this.userInfoHandle);
    }

    handleSubmit = (event) => {
        event.preventDefault();
        axios({
            url: '/api/logout',
            method: 'POST',
            headers: {
                'content-type': 'application/x-www-form-urlencoded'
            },
            data: querystring.stringify({
                username: this.state.username,
                password: this.state.password
            })
        }).then(() => {
            userService.refreshUserInfo();
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

    render() {
        return (
            <main>
                <form className="login-form" onSubmit={this.handleSubmit}>
                    <div className="login-form-title">
                        Logout
                    </div>
                    <div>
                        <span className="login-form-prompt">{
                            this.state.username
                                ? "See you, " + this.state.username
                                : "How did you get here, Anon?"
                        }
                        </span>
                    </div>
                    <div>
                        <input
                            className="login-form-submit"
                            type="submit"
                            value="Logout"
                        />
                    </div>
                </form>
            </main>
        )
    }
}