import React from 'react';

export class Card extends React.Component {
  render() {

    const cardClass = `card ${this.props.shadow && "shadow-lg"}`;
    return (
      <div className={cardClass} style={{ overflowY: "visible" }}>
        {this.props.header && (
            <div className="card-header py-3 d-flex flex-row align-items-center justify-content-between">
            <h6 className="m-0 font-weight-bold text-bright-1">{this.props.header}</h6>
            </div>
        )}
        <div className="card-body">
          {this.props.content}
        </div>
      </div>
    );
  };
};