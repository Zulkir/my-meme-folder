import React from "react";
import {Redirect} from 'react-router-dom';
import userService from "../services/UserService.js";

export default class HomePage extends React.Component {
    render() {
        const userInfo = userService.getUserInfo();
        return userInfo ? (
            <Redirect to={"/folder/" + userInfo.username} />
        ) : (
            <Redirect to={"/login"} />
        );
    }
}