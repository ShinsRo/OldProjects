import React, { Component } from 'react';

const defaultProps = {
    user:"default"
};
const User = ({user}) =>{
    console.log(user.userName)
    return(
        <div>
            <div>{user.posi}   {user.userName}</div>
        </div>
    );
}
User.defaultProps = defaultProps;

export default User;